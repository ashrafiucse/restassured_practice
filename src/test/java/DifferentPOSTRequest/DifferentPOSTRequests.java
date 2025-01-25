package DifferentPOSTRequest;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.json.JSONObject;
import org.json.JSONTokener;
import org.testng.annotations.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.HashMap;

public class DifferentPOSTRequests {
    POJOClassForThisAPI data = new POJOClassForThisAPI();
    String id;

    //POST Method request using HashMap
//    @Test(priority = 1)
    void requestUsingHashMap() {
        HashMap data = new HashMap();
        data.put("name", "Ashraf");
        data.put("location","Dhaka");
        data.put("phone", "01938748");
        String coursesArr[] = {"Java","C++"};
        data.put("courses",coursesArr);

        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("http://localhost:3000/students")
                .jsonPath().getString("id");
//                .then()
//                .statusCode(201)
//                .body("name",equalTo("Ashraf"))
//                .body("location",equalTo("Dhaka"))
//                .body("phone", equalTo("01938748"))
//                .body("courses[0]",equalTo("Java"))
//                .body("courses[1]", equalTo("C++"))
//                .header("Content-Type","application/json")
//                .log().all();
    }

    //POST Method request using Json.org library
    //@Test(priority = 1)
    void requestUsingJsonLibrary() {
        JsonObject data = new JsonObject();
        data.addProperty("name", "Ashraf");
        data.addProperty("location", "Dhaka");
        data.addProperty("phone", "01938748");

        // Create the JSON array for courses
        JsonArray coursesArr = new JsonArray();
        coursesArr.add("Java");
        coursesArr.add("C++");

        // Add the array to the JSON object
        data.add("courses", coursesArr);

        id = given()
                .contentType("application/json")
                .body(data.toString())

                .when()
                .post("http://localhost:3000/students")
                .jsonPath().getString("id");
    }
    
    //POST Method Request using POJO Class
    //@Test(priority = 1)
    void requestUsingPOJOClass() {
        data.setName("Ashraf");
        data.setLocation("Dhaka");
        data.setPhone("01938748");
        String courses[] = {"Java", "C++"};
        data.setCourses(courses);

        id = given()
                .contentType("application/json")
                .body(data)

                .when()
                .post("http://localhost:3000/students")
                .jsonPath().getString("id");
    }

    //POST Method Request using External Json File
    @Test(priority = 1)
    void requestUsingExternalJsonFile() throws FileNotFoundException {
        // Load the JSON file
        File file = new File(".\\body.json");
        FileReader fileReader = new FileReader(file);
        JSONTokener jsonTokener = new JSONTokener(fileReader);

        // Parse the JSON file into a JSONObject
        JSONObject data = new JSONObject(jsonTokener);

        id = given()
                .contentType("application/json")
                .body(data.toString())

                .when()
                .post("http://localhost:3000/students")
                .jsonPath().getString("id");
    }

    @Test(priority = 2)
    void getUser() {
        given()
                .when()
                .get("http://localhost:3000/students/" + id)
                .then()
                .statusCode(200)
                .body("name",equalTo("Ashraf"))
                .body("location",equalTo("Dhaka"))
                .body("phone", equalTo("01938748"))
                .body("courses[0]",equalTo("Java"))
                .body("courses[1]", equalTo("C++"))
                .header("Content-Type","application/json")
                .log().all();
    }

    @Test(priority = 3)
    void deleteUser() {
        given()
                .when()
                .delete("http://localhost:3000/students/" + id)
                .then()
                .statusCode(200);
    }
}
