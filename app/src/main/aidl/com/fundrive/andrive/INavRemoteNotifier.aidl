// INavRemoteNotifier.aidl
package com.fundrive.andrive;

// Declare any non-default types here with import statements

interface INavRemoteNotifier {
   void onNotify(in int ia_cmd, in String ia_json);
}
