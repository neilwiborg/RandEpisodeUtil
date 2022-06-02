package randepisodeutil;

/**
 *
 * @author neil
 */
public class Arguments {
    private char mode;
    private String filename;
    
    public Arguments(String[] args) {
        parseArgs(args);
    }
    
    private void parseArgs(String[] args) throws IllegalArgumentException {
        if (args.length == 0) {
            mode = 'n';
        } else {
            String programMode = args[0];
            if (programMode.charAt(0) != '-') {
                throw new IllegalArgumentException("Invalid arguments!");
            }
            mode = programMode.charAt(1);
            filename = args[1];
        }
    }
    
    private boolean validArgLength(String[] args) {
        final int arg_length_modeRandom_modeRemove = 2;
        final int arg_length_modeNew = 0;
        return args.length == arg_length_modeRandom_modeRemove ||
                args.length == arg_length_modeNew;
    }
    
    public char getMode() {
        return mode;
    }
    
    public String getFileName() {
        return filename;
    }
}
