package uk.co.automatictester.structural.facade;

import uk.co.automatictester.structural.facade.subsystem.Analyzer;
import uk.co.automatictester.structural.facade.subsystem.Parser;
import uk.co.automatictester.structural.facade.subsystem.Reporter;

public class Facade {

    private Parser parser = new Parser();
    private Analyzer analyzer = new Analyzer();
    private Reporter reporter = new Reporter();

    public void process() {
        parser.parse();
        analyzer.analyze();
        reporter.report();
    }
}
