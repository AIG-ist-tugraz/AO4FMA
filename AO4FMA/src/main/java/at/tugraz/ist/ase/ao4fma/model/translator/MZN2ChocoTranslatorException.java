/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2021-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.model.translator;

/**
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
public class MZN2ChocoTranslatorException extends RuntimeException {
    public MZN2ChocoTranslatorException(String mess) {
        super(mess);
    }
}
