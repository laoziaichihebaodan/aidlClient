// INavRemoteRequest.aidl
package com.fundrive.andrive;

// Declare any non-default types here with import statements
import com.fundrive.andrive.INavRemoteNotifier;

interface INavRemoteRequest {
    /** Request operation to navi service. */
    void request(in int ia_cmd, in String ia_json);

    /** set listener to handle messages from navi app
     *
     */
    void addListener(in INavRemoteNotifier notifier);
    void removeListener(in INavRemoteNotifier notifier);
}
