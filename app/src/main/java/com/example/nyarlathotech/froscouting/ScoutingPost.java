package com.example.nyarlathotech.froscouting;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ScoutingPost {

    /*
        This class sends or "posts" data to google sheets
        Each entry is an object in HTML for sheets to read, and the correlating name is the object
        All the vague objects are meant for customization (Obj1-32)

        **NOTE**
        **IF YOU DON'T UNDERSTAND LEARN RETROFIT **
    */

    @POST("formResponse")
    @FormUrlEncoded
    Call<SerializedData> savePost(@Field("entry.1955826634") String Scout,
                                  @Field("entry.932395635") String Team,
                                  @Field("entry.2079274681") String Position,
                                  @Field("entry.2035698356") String Match,
                                  @Field("entry.1990751905") String Obj1,
                                  @Field("entry.951731743") String Obj2,
                                  @Field("entry.676493950") String Obj3,
                                  @Field("entry.56810673") String Obj4,
                                  @Field("entry.1576806052") String Obj5,
                                  @Field("entry.1698851026") String Obj6,
                                  @Field("entry.493595288") String Obj7,
                                  @Field("entry.1980453348") String Obj8,
                                  @Field("entry.1969184503") String Obj9,
                                  @Field("entry.892792854") String Obj10,
                                  @Field("entry.468850361") String Obj11,
                                  @Field("entry.934558003") String Obj12,
                                  @Field("entry.477911469") String Obj13,
                                  @Field("entry.1134262734") String Obj14,
                                  @Field("entry.223171706") String Obj15,
                                  @Field("entry.1470643266") String Obj16,
                                  @Field("entry.556585741") String Obj17,
                                  @Field("entry.1607616538") String Obj18,
                                  @Field("entry.941683632") String Obj19,
                                  @Field("entry.1633037719") String Obj20,
                                  @Field("entry.1122372135") String Obj21,
                                  @Field("entry.1345782814") String Obj22,
                                  @Field("entry.1666090452") String Obj23,
                                  @Field("entry.470463983") String Obj24,
                                  @Field("entry.511003172") String Obj25,
                                  @Field("entry.1914660102") String Obj26,
                                  @Field("entry.235433421") String Obj27,
                                  @Field("entry.764380940") String Obj28,
                                  @Field("entry.1572325521") String Obj29,
                                  @Field("entry.808044220") String Obj30);
}
