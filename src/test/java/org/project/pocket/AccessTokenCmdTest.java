package org.project.pocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.project.pocket.data.AccessTokenData;

class AccessTokenCmdTest {

    @Test
    public void testAccessTokenCmdForModifiers() {
        AccessTokenData accessTokenData = new AccessTokenData("https://t.me/dhh");

        try {
            String jsonResult = new ObjectMapper().writeValueAsString(accessTokenData);
            System.out.println(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}