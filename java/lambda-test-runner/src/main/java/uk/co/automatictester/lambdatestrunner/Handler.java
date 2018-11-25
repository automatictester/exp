package uk.co.automatictester.lambdatestrunner;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class Handler implements RequestHandler<Request, Response> {

    private static final Logger log = LogManager.getLogger(Handler.class);

    // TODO: cleanup
    @Override
    public Response handleRequest(Request request, Context context) {
        File targetDir = new File(request.getTargetDir());
        try {
            FileUtils.deleteDirectory(targetDir);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String repoUri = request.getRepoUri();
        GitCloner.cloneRepo(repoUri, targetDir);

        List<String> command = Arrays.asList(request.getCommand().split(" "));

        String log = request.getLogFile();
        File logFile = new File(log);
        ProcessRunner.runProcess(command, targetDir, logFile);

//        logOutput(log);
        return null;
    }

    private void logOutput(String logFile) {
        Path path = Paths.get(logFile);
        Stream<String> output;
        try {
            output = Files.lines(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        output.forEach(System.out::println);
    }
}
