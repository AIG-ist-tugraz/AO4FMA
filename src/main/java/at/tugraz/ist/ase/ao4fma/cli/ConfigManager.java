/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2022-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.cli;

import at.tugraz.ist.ase.hiconfit.common.LoggerUtils;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

/**
 * A class to manage the input configuration.
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
@Getter
@ToString
@Slf4j
public final class ConfigManager {
    public static String defaultConfigFile = "./data/app.cfg";

    private final File fmFile;
    private final File filterFile;
    private final File productsFile;
    private final File transactionsFile;
    private final String queries_folder;
    private final String resultsFile;

    private static ConfigManager instance = null;

    public static ConfigManager getInstance(String configFile) {
        if (instance == null) {
            instance = new ConfigManager(configFile);
        }
        return instance;
    }

    private ConfigManager(String configFile) {
        Properties appProps = new Properties();
        try {
            appProps.load(new FileInputStream(configFile));
        } catch (IOException e) {
            log.error("{}{}", LoggerUtils.tab(), e.getMessage());
        }

        fmFile = new File(appProps.getProperty("fmFile", "./data/camera.xml"));
        filterFile = new File(appProps.getProperty("filterFile", "./data/filter.mzn"));
        productsFile = new File(appProps.getProperty("productsFile", "./data/products.csv"));
        transactionsFile = new File(appProps.getProperty("transactionsFile", "./data/transactions.csv"));
        queries_folder = appProps.getProperty("queries_folder", "./data/query/");
        resultsFile = appProps.getProperty("results", "results.csv");

        log.trace("{}<<< Read configurations [fmFile={}, filterFile={}, productsFile={}, transactionsFile={}, queries_folder={}, resultsFile={}]",
                LoggerUtils.tab(), fmFile, filterFile, productsFile, transactionsFile, queries_folder, resultsFile);
    }
}
