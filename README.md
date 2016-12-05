#Perm for Android#

**Perm** is library that makes it simple to **check and request Android Permissions** at runtime (like Camera or GPS) for Apps that target Android 6 or more.

## Usage ##

To Check and Request a permission, simply create a **Perm** instance, and use **isGranted**, **isDenied** or **askPermission** methods:

```java

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSION_CAMERA_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        Perm cameraPermission = new Perm(this, Manifest.permission.CAMERA);
        if (cameraPermission.isGranted()) {
            Toast.makeText(this, "Camera Permission already granted", Toast.LENGTH_LONG).show();
        } else if (cameraPermission.isDenied()) {
            Toast.makeText(this, "Camera Permission already denied", Toast.LENGTH_LONG).show();
        } else {
            cameraPermission.askPermission(PERMISSION_CAMERA_REQUEST);
        }
    }
}
```

To handle the result of the request, create a **PermResult** in the **onRequestPermissionsResult** of your Activity, and use **isGranted**, or **isDenied** methods.

```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    if (requestCode == PERMISSION_CAMERA_REQUEST) {
        PermResult cameraPermissionResult = new PermResult(permissions, grantResults);
        if (cameraPermissionResult.isGranted()) {
            Toast.makeText(this, "Camera Permission granted", Toast.LENGTH_LONG).show();
        } else if (cameraPermissionResult.isDenied()) {
            Toast.makeText(this, "Camera Permission denied", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Camera Permission cancelled", Toast.LENGTH_LONG).show();
        }
    }
}
```

## Installation with gradle

Add the following maven{} line to your **PROJECT** build.gradle file

```
allprojects {
    repositories {
        jcenter()
        maven { url "https://jitpack.io" }    // add this line
    }
}
```

Add the libary dependency to your **APP** build.gradle file

```
dependencies {
    compile 'com.github.smart-fun:Perm:1.0.0'    // add this line
}
```

##License##

Copyright 2016 Arnaud Guyon

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

