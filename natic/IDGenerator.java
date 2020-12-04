package natic;

/**
 * A simple linear ID generator
 */
public class IDGenerator {
    private String IDPrefix;
    private int GeneratorState;

    public IDGenerator(String iDPrefix, int generatorState) {
        IDPrefix = iDPrefix;
        GeneratorState = generatorState;
    }
    
    public String next() {
        GeneratorState += 1;
        String r = String.format("%s%08d", IDPrefix, GeneratorState);
        return r;
    }
}
