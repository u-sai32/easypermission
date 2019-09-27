
Setp 1. Add it in your root build.gradle at the end of repositories:
  	
		repositories {
			maven { url 'https://jitpack.io' }	
		}
  
Step 2. Add the dependency
  
  	dependencies {
	        implementation 'com.github.gamerMafia:EasyPermission:0.1.0'
	}
	
	
Step 3.	
  
                new EasyPermission.Builder()
                        .with(this)
                        .addPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA)
                        .setPermissionCheckListener(new PermissionStatusListener() {
                            @Override
                            public void onPermissionSuccess() {
                                    //this is your true part  
                            }

                            @Override
                            public void onPermissionCancel() {
                                //this is your false part  
                            }
                        })
                        .startPermission()
                        .build();


// Add permision you can add any permision with ","
		
	.addPermissions(READ_EXTERNAL_STORAGE, WRITE_EXTERNAL_STORAGE, CAMERA)

//also use can start permision outside the bilder
		
		startPermission();

//onPermissionSuccess() is your permision allow part

	@Override
	public void onPermissionSuccess() {
	    //this is your true part  

	}

//onPermissionCancel() is your permision deny part

	@Override
	public void onPermissionCancel() {
	    //this is your false part  
	}
