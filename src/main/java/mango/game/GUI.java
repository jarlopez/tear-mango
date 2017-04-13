package mango.game;

import mango.core.drupe.video.GraphicsUtil;

import java.util.Random;

import static org.lwjgl.opengl.GL11.*;

public class GUI {
    public void drawMenu() {
        Random rnd = new Random();
        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        int numEntries = 2;
        for (int i = 0; i < numEntries; i++) {
            float[] color = new float[4];
            float[] active1 = new float[4];
            float[] active2 = new float[4];
            for (int j = 0; j < 4; j++) {
                color[j] = rnd.nextFloat();
            }
            glColor4f(color[0], color[1], color[2], color[3]);
            GraphicsUtil.drawText(null, 20, 20, 20, "tmp");
        }
        glDisable(GL_BLEND);
    }
}


/*

  glEnable(GL_BLEND);
  glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
  for(i = 0; i < nEntries; i++) {
    if(i == iActiveItem - 1) {
      float color[4];
      float active1[4];
      float active2[4];
      int j;
      float t;
      int time = nebu_Time_GetElapsed() & 4095;
      t = sinf( time * M_PI / 2048.0 ) / 2.0f + 0.5f;

			scripting_GetGlobal("menu_item_active1", NULL);
      scripting_GetFloatArrayResult(active1, 4);
			scripting_GetGlobal("menu_item_active2", NULL);
      scripting_GetFloatArrayResult(active2, 4);

      for(j = 0; j < 4; j++) {
			color[j] = t * active1[j] + (1 - t) * active2[j];
      }
      glColor4f(color[0], color[1], color[2], color[3]);
    } else {
      float color[4];
      scripting_GetGlobal("menu_item", NULL);
      scripting_GetFloatArrayResult(color, 4);
      glColor4f(color[0], color[1], color[2], color[3]);
    }

      {
	char line_label[100];
	char line_data[100];
	scripting_RunFormat("return "
			    "GetMenuValueString( Menu.%s.items[%d] )",
			    pMenuName, i + 1);
	scripting_CopyStringResult(line_data, sizeof(line_data));

	if(line_data[0] != 0)
	  scripting_RunFormat("return "
			      "Menu[Menu.%s.items[%d]].caption .. ': '",
			      pMenuName, i + 1);
	else
	  scripting_RunFormat("return "
			      "Menu[Menu.%s.items[%d]].caption",
			      pMenuName, i + 1);

	scripting_CopyStringResult(line_label, sizeof(line_label));

	drawText(pFont, (float)x, (float)y, (float)size, line_label);
	drawText(pFont, (float)x + max_label * size, (float)y, (float)size, line_data);
      }
    y -= lineheight;
            }

            glDisable(GL_BLEND);
*/