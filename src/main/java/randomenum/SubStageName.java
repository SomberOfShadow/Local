package randomenum;

import java.util.Random;

public enum SubStageName {

    ADP_FM_VDU_STAGING("adp_fm_vdu_staging"),
    BR_SERVICE_STAGING("br_service_stage"),
    CM_SERVICE_STAGING("cm_service_staging"),
    DDC_SERVICE_STAGING("ddc_service_staging");

    private String value;

    private static Random random = new Random();
    SubStageName(String value){
        this.value = value;
    }

    public static String getValueRandom(){
        int pick = random.nextInt(SubStageName.values().length);
        return SubStageName.values()[pick].value;
    }
}
