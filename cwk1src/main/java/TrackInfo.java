import java.text.DecimalFormat;

/**
 * Program to provide information on a GPS track stored in a file.
 *
 * @author Teon Green
 */
public class TrackInfo {
  public static void main(String[] args) {
    if (args.length == 0){
      System.exit(0);
    }
    Track myTrack = new Track(args[0]);
    System.out.format("%d points in the track\n", myTrack.size());
    System.out.print("Lowest point is ");
    System.out.println(myTrack.lowestPoint());
    System.out.print("Highest point is ");
    System.out.println(myTrack.highestPoint());
    DecimalFormat distFormat = new DecimalFormat("#.###");
    String dist = distFormat.format(myTrack.totalDistance()/1000);
    String speed = distFormat.format(myTrack.averageSpeed());
    System.out.format("Total distance = %s km\nAverage speed = %s m/s", dist, speed);
    // TODO: Implementation TrackInfo application here
  }
}
