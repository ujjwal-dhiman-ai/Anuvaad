# Anuvaad App

This is an Android Application that can translate language of source text into multiple languages.

An external tool is used in this project, knowns as Google App Script.

You have to create a script in Google App Script script editor. Visit script.google.com to get started with Google App Script.

When you come across with editor in Google App Script, paste the below script in your editor and save your work by giving a name 
to your script.

# Google App Script

```
var mock = {
  parameter:{
    q:'hello',
    source:'en',
    target:'fr'
  }
};


function doGet(e) {
  e = e || mock;

  var sourceText = ''
  if (e.parameter.q){
    sourceText = e.parameter.q;
  }

  var sourceLang = '';
  if (e.parameter.source){
    sourceLang = e.parameter.source;
  }

  var targetLang = 'en';
  if (e.parameter.target){
    targetLang = e.parameter.target;
  }

  var translatedText = LanguageApp.translate(sourceText, sourceLang, targetLang, {contentType: 'html'});

  return ContentService.createTextOutput(translatedText).setMimeType(ContentService.MimeType.JSON);
}
```

After saving your script with appropriate name, Click on publish button -> Deploy as Web App, then a new dialog box will pop-up
here you have to set accessibiliy to your script, set it to "Anyone, even anonymous" and finally click Deploy. You will get your 
URL, copy and keep it safe we will use it further in our project.

