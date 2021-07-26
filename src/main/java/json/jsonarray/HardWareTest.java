package json.jsonarray;

public class HardWareTest {

    public String nodeId;

    public String date;

     public String productionDate;




    public HardWareTest() {
    }

    public HardWareTest(String nodeId, String date) {
        this.nodeId = nodeId;
        this.date = date;
    }

    public String getNodeId() {
        return nodeId;
    }

    public String getProductionDate() {
        return productionDate;
    }

    public String getDate() {
        return date;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
}
