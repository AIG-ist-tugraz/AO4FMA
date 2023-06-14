/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import lombok.Setter;

import java.io.BufferedWriter;

public abstract class AnalysisOperation {
    @Setter
    BufferedWriter writer = null;
    @Setter
    boolean printResults = true;
}
