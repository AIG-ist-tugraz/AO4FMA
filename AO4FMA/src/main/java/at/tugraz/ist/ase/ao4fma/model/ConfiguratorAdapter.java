/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.model;

import at.tugraz.ist.ase.ao4fma.common.ProductCollection;
import at.tugraz.ist.ase.ao4fma.common.ProductsReader;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.ISolutionTranslatable;
import at.tugraz.ist.ase.hiconfit.cacdr_core.translator.fm.FMSolutionTranslator;
import at.tugraz.ist.ase.hiconfit.configurator.Configurator;
import at.tugraz.ist.ase.hiconfit.kb.core.KB;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ConfiguratorAdapter extends Configurator {

//    private final Configurator configurator;
    private final ProductAwareConfigurationModel model;

//    @Getter
    private final ProductCollection products;

    @Getter
    protected final List<Solution> solutions = new LinkedList<>();

    @Builder(builderMethodName = "configuratorAdapterBuilder")
    public ConfiguratorAdapter(@NonNull KB kb,
                               @NonNull ProductAwareConfigurationModel model,
                               ISolutionTranslatable translator,
                               @NonNull File productsFile) {
        super(kb, model, translator, null);
        this.model = model;

        // read products
        try {
            products = ProductsReader.read(productsFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

//        this.configurator = Configurator.builder()
//                .kb(kb)
//                .configurationModel(model)
//                .translator(new FMSolutionTranslator())
//                .build();
    }

    public void findAllSolutions() {
        findAllSolutions(false,0);

        // filter solutions
        super.getSolutions().forEach(solution -> {
            if (products.contains(solution)) {
                solutions.add(solution);
            }
        });

    }

    @Override
    protected Solution getCurrentSolution() {
//        List<Assignment> assignments = kb.getVariableList().stream()
//                .map(var -> Assignment.builder()
//                        .variable(var.getName())
//                        .value(var.getValue())
//                        .build())
//                .collect(Collectors.toCollection(LinkedList::new));
        List<Assignment> assignments = model.getFilterVariableList().stream()
                .map(var -> Assignment.builder()
                        .variable(var.getName())
                        .value(var.getValue())
                        .build())
                .collect(Collectors.toCollection(LinkedList::new));

        return Solution.builder().assignments(assignments).build();
    }
}
