package com.enseval.ttss.util;

public interface AuthenticationService
{
    boolean login(String p0, String p1, String p2);
    
    void logout();
    
    UserCredential getUserCredential();
}
