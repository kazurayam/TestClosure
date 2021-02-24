# TestClosure

This is a [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demonstration purpose. You can download the zip from [Releases](https://github.com/kazurayam/TestClosure/releases) page, unzip it, open it with your local Katalon Studio.

This project was developed using Katalon Studiio v7.9.1. But it should work on all KS 7.0+.

This project proposes a new concept **TestClosure** for Katalon Studio. This project provides a preliminary implementation and demonbstrations as a proof of concept.

## Demo videos

You can view the videos by clicking the following links: 

| demo video | Test Suite elasped (seconds) | |
|------------|--------------------|---|
| [demo1A](https://drive.google.com/file/d/1_IPbM2UniK6N1eudSlNKsVoolJCE-HLy/view?usp=sharing) | 27 | visits 3 URLs sequentially, windows are not managed|
| [demo1B](https://drive.google.com/file/d/1ZxymFzjS1NFCaAm-CQaFbrG0r-QsXLqp/view?usp=sharing) | 30 | visits 3 URLs sequetioally, windows are managed in Tile layout|
| [demo1C](https://drive.google.com/file/d/1SklnOF8-Kov4hm0mHDddHxaDaafs6LDK/view?usp=sharing) | 55 | visits 5 URLs sequentially, windows are managed in Stack layout, takes screenshots |
| [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) | 20 | visits 3 URLs simultaneously, windows are overlayed at the same (x,y) position with the same width/height|
| [demo2B](https://drive.google.com/file/d/1-MKKXkGclWOOsdvKgrL5Z7DyWRt_TemH/view?usp=sharing) | 21 | visits 3 URLs simultaneously, windows are managed in Tile layout |
| [demo2C](https://drive.google.com/file/d/1xkq50B8gOLIskDTrjad_fJ-ZC_5M9mZK/view?usp=sharing) | 48 | visits 5 URLs simultaneously, windows are managed in Stacklayout, takes screenshots |

- Mac Book Air, Processor: 1.6GHz Dual Core Intel Core i5, Memory 16GB
- macOS Big Sur
- Katalon Studio v7.9.1

When I executed a Test Suite in Katalon Studio to take the [demo2A](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing), it acutally took 20 seconds to finish processing. But you will find the ["movie"](https://drive.google.com/file/d/1sS2D8SLUwMKuarHnqBvr7WJgSs-cNJRe/view?usp=sharing) plays in 7 seconds. The "movie" plays far quicker than the acutal scene. It seems that the movies are compressed by chomping the still frames off.