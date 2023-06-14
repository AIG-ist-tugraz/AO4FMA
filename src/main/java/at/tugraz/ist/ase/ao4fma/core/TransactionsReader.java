/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.core;

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

@Slf4j
@UtilityClass
public class TransactionsReader {
    public TransactionList read(@NonNull File file) throws IOException {
        log.debug("{}Reading the transaction file - {} >>>", LoggerUtils.tab(), file.getName());

        @Cleanup BufferedReader reader = Files.newReader(file, Charsets.UTF_8);

        TransactionList transactions = new TransactionList();

        String line = reader.readLine();
        while (line != null) {
            String[] tokens = line.split(",");

            int id = Integer.parseInt(tokens[0].trim());
            int ur_id = Integer.parseInt(tokens[1].trim());
            String product_id = tokens[2].trim();

            Transaction transaction = new Transaction(id, ur_id, product_id, null);
            transactions.add(transaction);

            line = reader.readLine();
        }

        return transactions;
    }
}
