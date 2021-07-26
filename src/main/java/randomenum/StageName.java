package randomenum;


import java.util.Random;

public enum StageName {
    ADPNEVERDOWN_STAGING("AdpNeverdown_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    ADP_STAGING("Adp_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    BAM_STAGING("Bam_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    CCD_STAGING("Ccd_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    CCES_STAGING("Cces_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    CDD_STAGING("Cdd_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    },
    CLOUD_RAN_STAGING("Cloud_ran_Staging"){
        @Override
        public String toString() {
            return super.toString();
        }
    };

    private String value;
    private static Random random = new Random();

    StageName(String value) {
        this.value = value;
    }

    public static StageName getValueRandom() {
        int pick  = random.nextInt(StageName.values().length);
        return StageName.values()[pick];
    }

}
