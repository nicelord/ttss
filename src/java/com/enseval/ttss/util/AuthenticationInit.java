package com.enseval.ttss.util;

import org.zkoss.zk.ui.util.*;
import java.util.*;
import org.zkoss.zk.ui.*;

public class AuthenticationInit implements Initiator
{
    AuthenticationService authService;
    
    public AuthenticationInit() {
        this.authService = new AuthenticationServiceImpl();
    }
    
    public void doInit(final Page page, final Map<String, Object> map) throws Exception {
        UserCredential cre = this.authService.getUserCredential();
        if (cre == null || cre.isAnonymous()) {
            Executions.sendRedirect("/login.zul");
        }
    }
}
