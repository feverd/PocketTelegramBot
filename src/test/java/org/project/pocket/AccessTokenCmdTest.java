package org.project.pocket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.project.pocket.commands.AccessTokenCmd;

class AccessTokenCmdTest {

    @Test
    public void testAccessTokenCmdForModifiers() {
        AccessTokenCmd accessTokenCmd = new AccessTokenCmd("https://t.me/dhh");

        try {
            String jsonResult = new ObjectMapper().writeValueAsString(accessTokenCmd);
            System.out.println(jsonResult);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}