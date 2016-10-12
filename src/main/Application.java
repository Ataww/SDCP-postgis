package main;

import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * Created by Tabs on 12/10/2016.
 */
public class Application {

    public static void main(String[] args) throws SQLException {
        Runner runner = new Runner();

        if(args.length < 1) {
            runner.mapMode();
        }
        else
        {
            runner.searchByNameMode(args[0]);
        }
    }

}
