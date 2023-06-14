/*
 * Analysis Operations On The Run: Beyond Static Feature Model Analysis in Constraint-based Recommenders
 *
 * Copyright (c) 2022-2023 AIG team, Institute for Software Technology, Graz University of Technology, Austria
 *
 * Contact: http://ase.ist.tugraz.at/ASE/
 */

package at.tugraz.ist.ase.ao4fma.cli;

import at.tugraz.ist.ase.hiconfit.common.CmdLineOptionsBase;
import lombok.Getter;
import lombok.NonNull;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

/**
 * Command line options for the WipeOutR applications.
 *
 * @author Viet-Man Le (vietman.le@ist.tugraz.at)
 */
public class CmdLineOptions extends CmdLineOptionsBase {

    @Getter
    @Option(name = "-cfg",
            aliases="--configuration-file",
            usage = "Specify the configuration file.")
    private String confFile = null;

    public CmdLineOptions(String banner, @NonNull String programTitle, String subtitle, @NonNull String usage) {
        super(banner, programTitle, subtitle, usage);

        parser = new CmdLineParser(this);
    }
}
