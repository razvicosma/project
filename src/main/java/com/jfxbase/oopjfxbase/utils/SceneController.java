package com.jfxbase.oopjfxbase.utils;

import com.jfxbase.oopjfxbase.utils.enums.SCENE_IDENTIFIER;

public class SceneController {
    public void changeScene(SCENE_IDENTIFIER newScene){
        ApplicationHandler.getInstance().changeScene(newScene);
    }
    public void changeScene(SCENE_IDENTIFIER newScene, User user, Apartment apartment) {ApplicationHandler.getInstance().changeScene(newScene,user,apartment);}
    public void closeApplication(){
        ApplicationHandler.getInstance().closeApplication();
    }
}