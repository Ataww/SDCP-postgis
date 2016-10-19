package main;

import java.sql.SQLException;

/**
 * Created by Tabs on 12/10/2016.
 */
public class Application {

    public static void main(String[] args) throws SQLException {
        Runner runner = new Runner();

        if (args.length > 0){
            if(args[0].equals("-q14")){
                runner.mapMode(MapMode.Q14);
            }else if(args[0].equals("-q13")){
                runner.mapMode(MapMode.Q13);
            }else{
                runner.searchByNameMode(args[0]);
            }
        }else{
            runner.mapMode(MapMode.BASIC);
        }
    }

}
