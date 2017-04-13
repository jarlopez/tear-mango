package mango.game;

public enum StateEvent {
    // Meta
    None,
    NotImplemented,

    // Real events
	Quit,
	GameEnd,
	GamePause,
	GameUnpause,
	GameCredits,
	GameEscape,
	Credits,
	GameLaunch,
	GUIEscape,
	GUIPrompt,
	GUIPromptEscape,
	PauseEscape,
}
