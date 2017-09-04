using UnityEngine;

public class Manager : MonoBehaviour
{
	// Player Prefab
	public GameObject player;
	
	// Title
	private GameObject title;
	
	void Start ()
	{
		// Search for the Title game object, and save it
		title = GameObject.Find ("Title");
	}
	
	void Update ()
	{
		// When not playing, check if the X key is being pressed.
		if (IsPlaying () == false && Input.GetKeyDown (KeyCode.X)) {
			GameStart ();
		}
	}
	
	void GameStart ()
	{
		// When it’s time to start the game, hide the title and make the player
		title.SetActive (false);
		Instantiate (player, player.transform.position, player.transform.rotation);
	}
	
	public void GameOver ()
	{
		// When the game ends, show the title.
		title.SetActive (true);
	}
	
	public bool IsPlaying ()
	{
		// Determine whether the game is being played by the visibility of the title.
		return title.activeSelf == false;
	}
}
