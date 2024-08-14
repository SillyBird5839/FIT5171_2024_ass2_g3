package org.example;

public class Airplane
    {
        private Integer airplaneID;
        private String airplaneModel;
        private Integer businessSitsNumber;
        private Integer economySitsNumber;
        private Integer crewSitsNumber;
    
        public Airplane(Integer airplaneID, String airplaneModel, Integer businessSitsNumber, Integer economySitsNumber, Integer crewSitsNumber)
        {
            setAirplaneID(airplaneID);
            setAirplaneModel(airplaneModel);
            setBusinessSitsNumber(businessSitsNumber);
            setEconomySitsNumber(economySitsNumber);
            setCrewSitsNumber(crewSitsNumber);
        }

    public int getAirplaneID() {
        return airplaneID;
    }

//    public void setAirplaneID(Integer airplaneID) {
//
//        if (airplaneID == null) {
//            throw new IllegalArgumentException("airplaneID cannot be null.");
//        }
//        if (airplaneID > 0) {
//            this.airplaneID = airplaneID;
//        } else {
////            System.out.println("Error: Airplane ID must be positive.");
//            throw new IllegalArgumentException("Airplane ID must be positive.");
//        }
//    }

        public void setAirplaneID(Integer airplaneID) {
            validateNotNull(airplaneID, "airplaneID");
            validatePositive(airplaneID, "Airplane ID");
            this.airplaneID = airplaneID;
        }

        private void validateNotNull(Integer value, String fieldName) {
            if (value == null) {
                throw new IllegalArgumentException(fieldName + " cannot be null.");
            }
        }

        private void validatePositive(Integer value, String description) {
            if (value <= 0) {
                throw new IllegalArgumentException(description + " must be positive.");
            }
        }


        public String getAirplaneModel() {
        return airplaneModel;
    }

    public void setAirplaneModel(String airplaneModel) {

        if (airplaneModel != null && !airplaneModel.isEmpty()) {
            this.airplaneModel = airplaneModel;
        } else {
//            System.out.println("Error: Airplane model cannot be null or empty.");
            throw new IllegalArgumentException("Airplane model cannot be null or empty.");
        }
    }

    public int getBusinessSitsNumber() {
        return businessSitsNumber;
    }

    public void setBusinessSitsNumber(Integer businessSitsNumber) {

        if (businessSitsNumber == null) {
            throw new IllegalArgumentException("BusinessSitsNumber cannot be null.");
        }
        if (businessSitsNumber >= 0 && businessSitsNumber <= 20) {
            this.businessSitsNumber = businessSitsNumber;
        } else {
//            System.out.println("Error: Business sits number must be between 1 and 300.");
            throw new IllegalArgumentException("Business sits number must be between 0 and 20.");
        }
    }

    public int getEconomySitsNumber() {
        return economySitsNumber;
    }

        public void setEconomySitsNumber(Integer economySitsNumber) {

            if (economySitsNumber == null) {
                throw new IllegalArgumentException("EconomySitsNumber cannot be null.");
            }
            if (economySitsNumber >= 0 && economySitsNumber <= 270) {
                this.economySitsNumber = economySitsNumber;
            } else {
//                System.out.println("Error: Economy sits number must be between 1 and 300.");
                throw new IllegalArgumentException("Economy sits number must be between 0 and 270.");
            }
        }

    public int getCrewSitsNumber() {
        return crewSitsNumber;
    }

    public void setCrewSitsNumber(Integer crewSitsNumber) {

        if (crewSitsNumber == null) {
            throw new IllegalArgumentException("CrewSitsNumber cannot be null.");
        }
        if (crewSitsNumber >= 1 && crewSitsNumber <= 10) {
            this.crewSitsNumber = crewSitsNumber;
        } else {
//            System.out.println("Error: Crew sits number must be between 1 and 300.");
            throw new IllegalArgumentException("Crew sits number must be between 1 and 10.");
        }
    }

    public String toString() {
            return "Airplane{" +
                    "model='" + getAirplaneModel() + "', " +
                    "business sits=" + getBusinessSitsNumber() + ", " +
                    "economy sits=" + getEconomySitsNumber() + ", " +
                    "crew sits=" + getCrewSitsNumber() +
                    '}';
        }


    public static Airplane getAirPlaneInfo(int airplane_id) {
        // TODO Auto-generated method stub
        return null;
    }
}