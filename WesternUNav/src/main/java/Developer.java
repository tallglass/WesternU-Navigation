/**
 *
 * @author orenj
 */
public class Developer extends User{
    
    public POI addPOI(Layer layer, int id, int roomNum, String name, float[] coordinate) {
        POI newPOI = new POI(layer, false, id, roomNum, name, coordinate);
        // ENTER INTO MAIN POI JSON HERE
        return newPOI;
    }
    
    // editPOI
    
    
    // deletePOI
        
}