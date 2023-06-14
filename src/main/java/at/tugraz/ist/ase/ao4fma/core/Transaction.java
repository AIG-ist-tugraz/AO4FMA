/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Requirement;

/**
 * A Transaction
 * @param id just an id
 * @param ur_id user requirement id
 * @param product_id id of the product that customer bought
 * @param req the user requirement
 */
public record Transaction(int id, int ur_id, String product_id, Requirement req) { }
