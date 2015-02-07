// generated by Fast Light User Interface Designer (fluid) version 1.0302

// $Header: /p/course/cs559-gleicher/private/CVS/GrTown/GraphicsTownUI.fl,v 1.4 2008/11/14 19:53:30 gleicher Exp $
// Note: this user interface (.cxx and .h) was created
// automatically by running fluid on the ".fl" file
// you should not edit the .cxx or .h file!

// Use "fluid" to edit the UI interactively
// CS559 Graphics Town
#include "GrTown_PCH.H"
#include "GraphicsTownUI.H"
#include "GrObject.H"
#include "GrWorld.H"
using std::vector;

void GraphicsTownUI::cb_pickCamera_i(Fl_Browser*, void*) {
  pickInteresting->deselect();
}
void GraphicsTownUI::cb_pickCamera(Fl_Browser* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_pickCamera_i(o,v);
}

void GraphicsTownUI::cb_time_i(Fl_Value_Slider*, void*) {
  townView->damage(1);
}
void GraphicsTownUI::cb_time(Fl_Value_Slider* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_time_i(o,v);
}

void GraphicsTownUI::cb_fov_i(Fl_Value_Slider*, void*) {
  townView->damage(1);
}
void GraphicsTownUI::cb_fov(Fl_Value_Slider* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_fov_i(o,v);
}

void GraphicsTownUI::cb_pickInteresting_i(Fl_Browser*, void*) {
  pickCamera->deselect();
follower->value(0);
}
void GraphicsTownUI::cb_pickInteresting(Fl_Browser* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_pickInteresting_i(o,v);
}

void GraphicsTownUI::cb_Fly_i(Fl_Button*, void*) {
  follower->value(0);
pickCamera->deselect();
pickInteresting->deselect();
townView->damage(1);
}
void GraphicsTownUI::cb_Fly(Fl_Button* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_Fly_i(o,v);
}

void GraphicsTownUI::cb_noWeatherEffect_i(Fl_Button*, void*) {
  townView->weathermodechage(0);
}
void GraphicsTownUI::cb_noWeatherEffect(Fl_Button* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_noWeatherEffect_i(o,v);
}

void GraphicsTownUI::cb_Rain_i(Fl_Button*, void*) {
  townView->weathermodechage(1);
}
void GraphicsTownUI::cb_Rain(Fl_Button* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_Rain_i(o,v);
}

void GraphicsTownUI::cb_Snow_i(Fl_Button*, void*) {
  townView->weathermodechage(2);
}
void GraphicsTownUI::cb_Snow(Fl_Button* o, void* v) {
  ((GraphicsTownUI*)(o->parent()->parent()->user_data()))->cb_Snow_i(o,v);
}

Fl_Double_Window* GraphicsTownUI::make_window() {
  { window = new Fl_Double_Window(1017, 637, "CS 559 Graphics Town!");
    window->labelsize(12);
    window->user_data((void*)(this));
    { TownViewWidget* o = townView = new TownViewWidget(0, 0, 835, 635);
      townView->box(FL_NO_BOX);
      townView->color(FL_BACKGROUND_COLOR);
      townView->selection_color(FL_BACKGROUND_COLOR);
      townView->labeltype(FL_NORMAL_LABEL);
      townView->labelfont(0);
      townView->labelsize(14);
      townView->labelcolor(FL_FOREGROUND_COLOR);
      townView->align(Fl_Align(FL_ALIGN_BOTTOM));
      townView->when(FL_WHEN_RELEASE);
      Fl_Group::current()->resizable(townView);
      o->ui = this;
    } // TownViewWidget* townView
    { Fl_Group* o = new Fl_Group(830, 0, 185, 635);
      { Fl_Browser* o = pickCamera = new Fl_Browser(850, 0, 155, 70, "view");
        pickCamera->type(2);
        pickCamera->callback((Fl_Callback*)cb_pickCamera);
        for(vector<GrObject*>::iterator g=theObjects.begin(); g != theObjects.end(); ++g)
        if((*g)->ridable) o->add((*g)->name.c_str(),*g);
      } // Fl_Browser* pickCamera
      { cull = new Fl_Button(840, 600, 90, 35, "Backface Cull");
        cull->type(1);
        cull->value(1);
        cull->selection_color((Fl_Color)11);
        cull->labelsize(12);
      } // Fl_Button* cull
      { time = new Fl_Value_Slider(860, 500, 115, 20, "Time of Day (24hr)");
        time->type(5);
        time->maximum(24);
        time->step(1);
        time->value(9);
        time->callback((Fl_Callback*)cb_time);
      } // Fl_Value_Slider* time
      { fov = new Fl_Value_Slider(990, 480, 15, 75, "FoV");
        fov->minimum(1);
        fov->maximum(180);
        fov->step(1);
        fov->value(45);
        fov->callback((Fl_Callback*)cb_fov);
      } // Fl_Value_Slider* fov
      { lgTex = new Fl_Button(930, 600, 85, 35, "textured ground");
        lgTex->type(1);
        lgTex->value(1);
        lgTex->selection_color((Fl_Color)11);
        lgTex->labelsize(10);
      } // Fl_Button* lgTex
      { follower = new Fl_Button(850, 90, 75, 20, "Follow");
        follower->type(1);
        follower->color(FL_DARK1);
        follower->selection_color((Fl_Color)11);
      } // Fl_Button* follower
      { rate = new Fl_Value_Output(925, 550, 50, 25, "frame rate");
        rate->maximum(100);
        rate->step(0.1);
      } // Fl_Value_Output* rate
      { speedup = new Fl_Value_Slider(905, 115, 105, 20, "carspeed");
        speedup->type(1);
        speedup->maximum(3);
        speedup->step(0.2);
        speedup->value(1);
        speedup->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* speedup
      { Fl_Browser* o = pickInteresting = new Fl_Browser(840, 365, 170, 85, "sights");
        pickInteresting->type(2);
        pickInteresting->callback((Fl_Callback*)cb_pickInteresting);
        for(vector<GrObject*>::iterator g=theObjects.begin(); g != theObjects.end(); ++g)
        if((*g)->interesting) o->add((*g)->name.c_str(),*g);
      } // Fl_Browser* pickInteresting
      { Fl_Button* o = new Fl_Button(925, 90, 65, 20, "Fly");
        o->callback((Fl_Callback*)cb_Fly);
      } // Fl_Button* o
      { noWeatherEffect = new Fl_Button(850, 145, 150, 20, "noWeatherEffect");
        noWeatherEffect->callback((Fl_Callback*)cb_noWeatherEffect);
      } // Fl_Button* noWeatherEffect
      { Rain = new Fl_Button(850, 165, 150, 20, "Rain");
        Rain->callback((Fl_Callback*)cb_Rain);
      } // Fl_Button* Rain
      { Snow = new Fl_Button(850, 185, 150, 20, "Snow");
        Snow->callback((Fl_Callback*)cb_Snow);
      } // Fl_Button* Snow
      { particleamount = new Fl_Value_Slider(905, 220, 105, 15, "Particle%");
        particleamount->type(1);
        particleamount->step(0.1);
        particleamount->value(1);
        particleamount->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* particleamount
      { HeliRotSpeed = new Fl_Value_Slider(915, 250, 95, 15, "HeliRotSpd");
        HeliRotSpeed->type(1);
        HeliRotSpeed->minimum(8);
        HeliRotSpeed->maximum(50);
        HeliRotSpeed->step(2);
        HeliRotSpeed->value(14);
        HeliRotSpeed->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* HeliRotSpeed
      { WindStrength = new Fl_Value_Slider(930, 280, 80, 15, "WindStrength");
        WindStrength->type(1);
        WindStrength->step(0.1);
        WindStrength->value(0.5);
        WindStrength->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* WindStrength
      { WaveSpd = new Fl_Value_Slider(905, 310, 100, 15, "WaveSpd");
        WaveSpd->type(1);
        WaveSpd->step(0.1);
        WaveSpd->value(0.2);
        WaveSpd->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* WaveSpd
      { Transparency = new Fl_Value_Slider(920, 335, 90, 15, "WaterTrans");
        Transparency->type(1);
        Transparency->step(0.1);
        Transparency->value(0.2);
        Transparency->align(Fl_Align(FL_ALIGN_LEFT));
      } // Fl_Value_Slider* Transparency
      o->end();
    } // Fl_Group* o
    window->end();
  } // Fl_Double_Window* window
  return window;
}
