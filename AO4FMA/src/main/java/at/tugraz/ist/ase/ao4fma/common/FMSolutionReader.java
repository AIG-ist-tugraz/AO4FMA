/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2022-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.common;

import at.tugraz.ist.ase.hiconfit.cacdr_core.Assignment;
import at.tugraz.ist.ase.hiconfit.cacdr_core.Solution;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import com.google.common.base.Charsets;
import com.google.common.io.Files;
import lombok.Cleanup;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

@UtilityClass
@Slf4j
public class FMSolutionReader {
    public List<Solution> read(@NonNull File file) throws IOException {
        log.debug("{}Reading the configuration file - {} >>>", LoggerUtils.tab(), file.getName());

        @Cleanup BufferedReader reader = Files.newReader(file, Charsets.UTF_8);

        List<Solution> solutions = new LinkedList<>();

        String line = reader.readLine();
        while (line != null) {
            List<Assignment> assignments = new LinkedList<>();

            String[] tokens = line.split(",");

            for (String token : tokens) {
                String[] items = token.split("=");

                String variable = items[0];
                String value = items[1];

                Assignment ur = Assignment.builder().variable(variable).value(value).build();

                assignments.add(ur);
            }

            solutions.add(Solution.builder().assignments(assignments).build());

            line = reader.readLine();
        }

        return solutions;
    }
}