import java.io.File;
import java.io.IOException;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.List;
import java.time.temporal.ChronoUnit;
/**
 * Represents a point in space and time, recorded by a GPS sensor.
 *
 * @author Teon Green
 */
public class Track {
    List<Point> points;

  // TODO: Create a stub for the constructor
    public Track(){
        //If no filename provided make empty list
        points = new ArrayList<>();
    }
    public Track(String filename) {
        points = new ArrayList<>();
        //handle IOException from incorrect file name
        try{
            readFile(filename);
        }
        catch (IOException e){
            throw new RuntimeException(e);
        }
    }
  // TODO: Create a stub for readFile()
    public void readFile(String filename) throws IOException{
        if (!points.isEmpty()){
            //Remove old data, if reading file and array isn't empty, reset array
            points = new ArrayList<>();
        }
        File readingFile = new File(filename);
        Scanner reader = new Scanner(readingFile);
        String buffer;
        double tLat, tLon, tElev;
        ZonedDateTime tTime;
        int startCheck = 0;
        //Looping through file
        while(reader.hasNext()){
            if (startCheck != 0) {
                buffer = reader.nextLine();
                //Parsing through each row of the file
                String[] parsedBuffer = buffer.split(",");
                if (parsedBuffer.length != 4){
                    //Give an error if all data isn't there
                    throw new GPSException("Data missing from point");
                }
                else {
                    //Set variables, and create a point
                    tLon = Double.parseDouble(parsedBuffer[1]);
                    tLat = Double.parseDouble(parsedBuffer[2]);
                    tElev = Double.parseDouble(parsedBuffer[3]);
                    tTime = ZonedDateTime.parse(parsedBuffer[0]);
                    Point tempPoint = new Point(tTime, tLon, tLat, tElev);
                    points.add(tempPoint);
                }
            }
            else{
                //The first line in every file isn't a record
                reader.nextLine();
                startCheck += 1;
            }
        }
        reader.close();
    }
  // TODO: Create a stub for add()
    public int size(){
        return points.size();
    }
  // TODO: Create a stub for get()
    public Point get(int index){
        int arraySize = size();
        //Handle invalid index's
        if (index >= arraySize || index < 0 || arraySize == 1){
            throw new GPSException("Index out of range");
        }
        else{
            return points.get(index);
        }
    }
  // TODO: Create a stub for size()
    public void add(Point point){
        points.add(point);
    }
  // TODO: Create a stub for lowestPoint()
    public Point lowestPoint(){
        //point with the lowest elevation
        int lowCount;
        Point pointBuffer, lowestPoint = null;
        double lowestElev = 999999;
        //Simple solutions
        if (size() == 1){
            lowestPoint = get(0);
        }
        else if(size() == 0){
            throw new GPSException("Cannot get lowest point of an empty track");
        }
        else {
            //Looping through the track
            for (lowCount = 0; lowCount < size(); lowCount++) {
                if (lowCount == 0) {
                    //if first iteration, set the first point in the track to the lowest
                    lowestPoint = get(lowCount);
                    lowestElev = lowestPoint.getElevation();
                } else {
                    pointBuffer = get(lowCount);
                    if (pointBuffer.getElevation() < lowestElev) {
                        //If current point is lower than the lowest point, set current to lowest
                        lowestPoint = pointBuffer;
                        lowestElev = lowestPoint.getElevation();
                    }
                }
            }
        }
    return lowestPoint;
}
  // TODO: Create a stub for highestPoint()
    public Point highestPoint(){
        //point with the highest elevation
        int highCount;
        Point pointBuffer, highestPoint = null;
        double highestElev = -999999;
        //Easy solutions
        if (size() == 1){
            highestPoint = get(0);
        }
        else if(size() == 0){
            throw new GPSException("Cannot get highest point of an empty track");
        }
        else {
            //Looping through the file
            for (highCount = 0; highCount < size(); highCount++) {
                if (highCount == 0) {
                    //set first point the highest point
                    highestPoint = get(highCount);
                    highestElev = highestPoint.getElevation();
                } else {
                    pointBuffer = get(highCount);
                    if (pointBuffer.getElevation() > highestElev) {
                        //if current point is greater than the highest point, set current to highest
                        highestPoint = pointBuffer;
                        highestElev = highestPoint.getElevation();
                    }
                }
            }
        }
    return highestPoint;
}
  // TODO: Create a stub for totalDistance()
    public double totalDistance(){
        int distCount;
        Point pointBuffer1, pointBuffer2;
        double totalDistance = 0, distBuffer;
        //Throw an exception if the size is less than 2
        if (size() < 2){
            throw new GPSException("Cannot calculate distance of track with less than 2 points");
        }
        else {
            //Looping through the track, to the element before the last
            for (distCount = 0; distCount < size() - 1; distCount++) {
                pointBuffer1 = get(distCount);
                pointBuffer2 = get(distCount + 1);
                //Get the distance between the current point and the point in front of it
                distBuffer = Point.greatCircleDistance(pointBuffer1, pointBuffer2);
                totalDistance += distBuffer;
            }
        }
    return totalDistance;
}
  // TODO: Create a stub for averageSpeed()
    public double averageSpeed(){
        //find total time
        int trackSize = size();
        double avgSpeed;
        //Throw an exception if the size of the track is less than 2
        if (trackSize < 2){
            throw new GPSException("Cannot computer average speed for a track with less than 2 points");
        }
        else {
            //Setting first and last point
            Point firstPoint = get(0), lastPoint = get(trackSize - 1);
            long totalTime;
            //Getting the time between the first and last point(given as a long)
            totalTime = ChronoUnit.SECONDS.between(firstPoint.getTime(), lastPoint.getTime());
            //Speed = distance/time
            avgSpeed = totalDistance()/totalTime;
        }
    return avgSpeed;
}
}
