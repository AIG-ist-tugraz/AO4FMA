/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.model;

import at.tugraz.ist.ase.ao4fma.model.translator.MZN2ChocoTranslator;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.configurator.ConfigurationModel;
import at.tugraz.ist.ase.hiconfit.kb.core.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.chocosolver.solver.variables.IntVar;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * This class extends the {@link ConfigurationModel} class by adding the filter constraints.
 */
@Slf4j
public class ProductAwareConfigurationModel extends ConfigurationModel {

    @Getter
    private MZN2ChocoTranslator translator;

    private final File filterFile;
    protected List<Domain> filterDomainList;
    @Getter
    protected List<Variable> filterVariableList;
    private final List<Constraint> filterConstraintList;

    @Builder
    public ProductAwareConfigurationModel(@NonNull KB kb, boolean rootConstraints,
                                          @NonNull File filterFile, @NonNull MZN2ChocoTranslator translator) {
        super(kb, rootConstraints);

        this.translator = translator;
        this.filterFile = filterFile;
        this.filterDomainList = new LinkedList<>();
        this.filterVariableList = new LinkedList<>();
        this.filterConstraintList = new LinkedList<>();
    }

    @Override
    public void initialize() {
        super.initialize();

        // translate the filter constraints
        try (InputStream filterStream = new FileInputStream(filterFile)) {
            translator.translate(filterStream, this);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // sets new correct constraints to super class
        log.trace("{}Adding correct constraints", LoggerUtils.tab());
        val B = new LinkedList<>(super.getCorrectConstraints());
        B.addAll(filterConstraintList);

        this.setCorrectConstraints(B);

        // remove all Choco constraints, cause we just need variables
        getModel().unpost(getModel().getCstrs());
    }

    public void addDomain(String domainName, List<String> domainValues) {
        filterDomainList.add(Domain.builder()
                .name(domainName)
                .values(domainValues)
                .build());
    }

    public Domain getDomain(String domainName) {
        return filterDomainList.stream()
                .filter(domain -> domain.getName().equals(domainName))
                .findFirst()
                .orElse(null);
    }

    public void addVariable(String varName, String domainName, IntVar intVar) {
        val domain = getDomain(domainName);
        val var = IntVariable.builder()
                .name(varName)
                .domain(domain)
                .chocoVar(intVar).build();
        filterVariableList.add(var);
    }

    public void addConstraint(Constraint constraint) {
        filterConstraintList.add(constraint);
    }

    public org.chocosolver.solver.variables.Variable getVariable(String varName) {
        return Arrays.stream(getModel().getVars()).filter(v -> v.getName().equals(varName)).findFirst().orElse(null);
    }
}
