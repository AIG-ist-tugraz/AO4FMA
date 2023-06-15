/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.configurator;

import at.tugraz.ist.ase.ao4fma.core.Product;
import at.tugraz.ist.ase.ao4fma.core.ProductAssortment;
import at.tugraz.ist.ase.ao4fma.core.mapper.IProductSolutionMapper;
import at.tugraz.ist.ase.ao4fma.model.ProductAwareConfigurationModel;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.ISolutionTranslatable;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import at.tugraz.ist.ase.hiconfit.kb.core.KB;
import lombok.Builder;
import lombok.NonNull;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Adapter for the Configurator class
 */
public class ConfiguratorAdapter extends Configurator {

    private final ProductAwareConfigurationModel model;
    private final ProductAssortment productAssortment;

    protected final List<Product> products = new LinkedList<>();

    // return a copy of the list
    public List<Product> getProducts() {
        return new LinkedList<>(products);
    }

    IProductSolutionMapper productSolutionMapper;

    @Builder(builderMethodName = "configuratorAdapterBuilder")
    public ConfiguratorAdapter(@NonNull KB kb,
                               @NonNull ProductAwareConfigurationModel model,
                               ISolutionTranslatable translator,
                               @NonNull ProductAssortment productAssortment,
                               @NonNull IProductSolutionMapper productSolutionMapper) {
        super(kb, model, translator, null);
        this.model = model;
        this.productAssortment = productAssortment;
        this.productSolutionMapper = productSolutionMapper;
    }

    public void findAllSolutions(Requirement requirement) {
        if (requirement != null) {
            setRequirement(requirement);
        }

        // clear products
        products.clear();
        emptySolutions();

        findAllSolutions(false, 0);

        // filter solutions
        super.getSolutions().forEach(solution -> {
            Product translatedProduct = productSolutionMapper.toProduct(solution);

            productAssortment.get(translatedProduct.properties()).ifPresent(product -> {
                Product newProduct = new Product(product.id(), product.properties(), translatedProduct.fm_values(), product.rf());

                products.add(newProduct);
            });
        });
    }

    @Override
    protected Solution getCurrentSolution() {
        List<Assignment> assignments = model.getFilterVariableList().stream()
                .map(var -> Assignment.builder()
                        .variable(var.getName())
                        .value(var.getValue())
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));

        kb.getVariableList().forEach(var -> assignments.add(Assignment.builder()
                                                .variable(var.getName())
                                                .value(var.getValue())
                                                .build()));

        return Solution.builder().assignments(assignments).build();
    }
}
