DartJsonFormat
------
参考[GsonFormat](https://github.com/zzz40500/GsonFormat)
制作的Android Studio和idea插件，也是根据json文本转换成对应的dart的model类
使用方法跟GsonFormat差不多，而生成代码是根据
[json_serializable](https://pub.dartlang.org/packages/json_serializable)的生成模板
##安装方法
[下载地址](https://github.com/ShikinChen/DartJsonFormat/releases)
1. 下载 DartJsonFormat-x.x.x.zip ,
2. Android studio  File->Settings..->Plugins -->
install plugin from disk..导入下载的 DartJsonFormat-x.x.x.zip ,
3. 重启 android studio .
 

##使用
使用方式跟[GsonFormat](https://github.com/zzz40500/GsonFormat)差不多，
创建一个空的dart文件，在当前dart文件右键选Generate然后选DartJsonFormat填写对应json文本就行。
## License

```
   Copyright 2018 The DartJsonFormat Authors

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

     http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
