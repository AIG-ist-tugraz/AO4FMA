/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma;

import at.tugraz.ist.ase.ao4fma.ao.Recommendation;
import at.tugraz.ist.ase.ao4fma.ao.UserRequirement;
import at.tugraz.ist.ase.ao4fma.cli.CmdLineOptions;
import at.tugraz.ist.ase.ao4fma.cli.ConfigManager;
import at.tugraz.ist.ase.ao4fma.core.rank.SimpleProductRankingStrategy;
import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import at.tugraz.ist.ase.hiconfit.common.RandomUtils;
import at.tugraz.ist.ase.hiconfit.fm.parser.FeatureModelParserException;
import com.opencsv.CSVWriter;
import com.opencsv.CSVWriterBuilder;
import lombok.Cleanup;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static at.tugraz.ist.ase.ao4fma.cli.ConfigManager.defaultConfigFileTransactions;

@Slf4j
public class FakeTransactionsGenerator {
    public static void main(String[] args) throws FeatureModelParserException, IOException {
        String programTitle = "Creating the fake transactions";
        String usage = "Usage: java -jar fake_trans.jar [options]]";

        // Parse command line arguments
        CmdLineOptions cmdLineOptions = new CmdLineOptions(null, programTitle, null, usage);
        cmdLineOptions.parseArgument(args);

        if (cmdLineOptions.isHelp()) {
            cmdLineOptions.printUsage();
            System.exit(0);
        }

        cmdLineOptions.printWelcome();

        String confFile = cmdLineOptions.getConfFile() == null ? defaultConfigFileTransactions : cmdLineOptions.getConfFile();
        ConfigManager cfg = ConfigManager.getInstance(confFile);

        val fmFile = cfg.getFmFile();
        val filterFile = cfg.getFilterFile();
        val productsFile = cfg.getProductsFile();
        val transactionsFile = cfg.getTransactionsFile();
        val numTransactions = cfg.getNumTransactions();

        LoggerUtils.setUseThreadInfo(false);
        createTransactionTable(fmFile, filterFile, productsFile, transactionsFile, numTransactions);
    }

    private static void createTransactionTable(File fmFile, File filterFile,
                                               File productsFile, File transactionsFile,
                                               int numTransactions)
            throws FeatureModelParserException, IOException {
        val message = String.format("%sCreating the transaction table ...", LoggerUtils.tab());
        log.info(message);

        // create a CSVWriter
        @Cleanup val csvWriter = new CSVWriterBuilder(new FileWriter(transactionsFile))
                .withSeparator(',')
                .withQuoteChar(CSVWriter.NO_QUOTE_CHARACTER)
                .build();

        // prepare the data
        val urOperation = new UserRequirement(fmFile, filterFile, productsFile);
        // get all user requirements
        val allURs = urOperation.getRequirements();
        // consistent user requirements
        val consistentURs = urOperation.getConsistentUserRequirements();

        // prepare the data
        LoggerUtils.indent();
        for (int i = 0; i < numTransactions; i++) {
            // randomly select a consistent UR
            val index = RandomUtils.getRandomInt(consistentURs.size());
            val selectedUR = consistentURs.get(index);
            val selectedURId = allURs.indexOf(selectedUR);

            val recommendation = Recommendation.builder()
                    .fmFile(fmFile)
                    .filterFile(filterFile)
                    .productsFile(productsFile)
                    .build();
            recommendation.setRankingStrategy(new SimpleProductRankingStrategy()); // set ranking strategy
            recommendation.setPrintResults(false);
            val recommendationList = recommendation.recommend(selectedUR);

            // randomly select a product
            val indexP = RandomUtils.getRandomInt(recommendationList.size());
            val selectedProduct = recommendationList.get(indexP);

            log.info("{}{}, {}, {}", LoggerUtils.tab(), i, selectedURId, selectedProduct.id());
            // write to the CSV file
            csvWriter.writeNext(new String[]{String.valueOf(i), String.valueOf(selectedURId), selectedProduct.id()});
            csvWriter.flushQuietly();
        }
        LoggerUtils.outdent();
    }
}
