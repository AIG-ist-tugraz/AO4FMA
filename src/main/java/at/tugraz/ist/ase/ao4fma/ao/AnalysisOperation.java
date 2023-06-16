/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.ao;

import lombok.NonNull;
import lombok.Setter;

import java.io.BufferedWriter;
import java.io.File;

/**
 * Base class for all analysis operations
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
public abstract class AnalysisOperation {
    @Setter
    protected BufferedWriter writer = null; // print results to file
    @Setter
    protected boolean printResults = true; // true to print results, false to not print results

    protected final File fmFile;
    protected final File filterFile;
    protected final File productsFile;

    public AnalysisOperation(@NonNull File fmFile, @NonNull File filterFile, @NonNull File productsFile) {
        this.fmFile = fmFile;
        this.filterFile = filterFile;
        this.productsFile = productsFile;
    }
}
