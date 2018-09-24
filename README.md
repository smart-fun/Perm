#Perm for Android#

**Perm** is a library that makes it simple to **check and request Android Permissions** at runtime (like Camera or GPS) for Apps targetting Android 6 or more.

Note that since version 1.1.1 of the library it is possible to request **several Permissions** at the same time.

## Usage ##

To Check and Request permissions, simply create a **Perm** instance, and use **isGranted()**, **areGranted()**, **isDenied()**, **areDenied()**, or **askPermissions()** methods:

```java

public class MainActivity extends AppCompatActivity {

    private static final int PERMISSIONS_REQUEST = 1;
    private static final String PERMISSIONS[] = {Manifest.permission.CAMERA, Manifest.permission.READ_CONTACTS};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main_activity);

        final Perm perm = new Perm(this, PERMISSIONS);
        if (perm.areGranted()) {
            Toast.makeText(this, "All Permissions granted", Toast.LENGTH_LONG).show();
        } else {
            perm.askPermissions(PERMISSIONS_REQUEST);
        }
    }
}
```

To handle the result of the request, create a **PermResult** in the **onRequestPermissionsResult** of your Activity, and use **isGranted()**, **areGranted()**, **isDenied()**, **areDenied()**, or **areCancelled()** methods.

```java
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSIONS_REQUEST) {
            PermResult permResult = new PermResult(permissions, grantResults);
            if (permResult.areCancelled()) {
                Toast.makeText(this, "Permission process cancelled", Toast.LENGTH_LONG).show();
            } else {
                for (String permission : PERMISSIONS) {
                    if (permResult.isGranted(permission)) {
                        Toast.makeText(this, permission + " granted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, permission + " denied", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }

    }
```

Don't forget to add the required permissions in your **AndroidManifest.xml** file! For example

```xml
    <uses-permission
        android:name="android.permission.CAMERA"
        android:required="false" />
        
    <uses-permission
        android:name="android.permission.READ_CONTACTS" />
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
    implementation 'com.github.smart-fun:Perm:1.1.2'    // add this line
}
```

##License##

Copyright 2016-2018 Arnaud Guyon

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

