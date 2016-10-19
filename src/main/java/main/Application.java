package main;

import java.sql.SQLException;

/**
 * Created by Tabs on 12/10/2016.
 */
public class Application {

    public static void main(String[] args) throws SQLException {
        Runner runner = new Runner();

        if (args.length > 0){
            if(args[0].equals("-q11")){
                runner.mapMode(MapMode.Q11);
            }else if(args[0].equals("-q12")){
                runner.mapMode(MapMode.Q12);
            }else{
                runner.searchByNameMode(args[0]);
            }
        }else{
            runner.mapMode(MapMode.BASIC);
        }
    }

}
