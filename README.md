# Record Unloading Issue

1. Run IDE, open project with some plain text files
2. Trigger plugin reload
3. Reload fails due to classloader leak:
   ```
     ROOT: Sticky class
     java.lang.runtime.ObjectMethods.OBJECT_EQ
     java.lang.invoke.DirectMethodHandle.asTypeCache
     java.lang.invoke.DirectMethodHandle.type
     java.lang.invoke.MethodType.ptypes
     java.lang.Class[]
     com.example.recorddemo.DemoIndex$IndexData.<loader>
   * com.intellij.ide.plugins.cl.PluginClassLoader
   ``` 