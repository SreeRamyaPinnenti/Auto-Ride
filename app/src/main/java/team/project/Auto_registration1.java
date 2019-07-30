package team.project;

/**
 * Created by $Yaswanth.Pinnenti on 25-09-2018.
 */
public class Auto_registration1 {
        String auto_no;
        String licence;
        String name;
        String mobile_no;
        String adhar_no;
        public Auto_registration1() {
        }
        public Auto_registration1(String auto_no, String licence, String name, String mobile_no, String adhar_no) {
            this.auto_no = auto_no;
            this.licence = licence;
            this.name = name;
            this.mobile_no = mobile_no;
            this.adhar_no = adhar_no;
        }

        public String getAuto_no() {
            return auto_no;
        }

        public String getLicence() {
            return licence;
        }

        public String getName() {
            return name;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public String getAdhar_no() {
            return adhar_no;
        }

        public void setAuto_no(String auto_no) {
            this.auto_no = auto_no;
        }

        public void setLicence(String licence) {
            this.licence = licence;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public void setAdhar_no(String adhar_no) {
            this.adhar_no = adhar_no;
        }
    }


