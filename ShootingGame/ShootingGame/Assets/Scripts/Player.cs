using UnityEngine;
using System.Collections;

public class Player : MonoBehaviour
{
	// Spaceship Component
	Spaceship spaceship;
	
	IEnumerator Start ()
	{
		// Get the Spaceship component
		spaceship = GetComponent<Spaceship> ();
		
		while (true) {
			
			// Make a bullet with the player’s position/rotation
			spaceship.Shot (transform);
			
			// Make a shot sound effect
			audio.Play();
			
			// Wait for shotDelay seconds
			yield return new WaitForSeconds (spaceship.shotDelay);
		}
	}
	
	void Update ()
	{
		// Right, Left
		float x = Input.GetAxisRaw ("Horizontal");
		
		// Up, Down
		float y = Input.GetAxisRaw ("Vertical");
		
		// Get the direction of movement
		Vector2 direction = new Vector2 (x, y).normalized;
		
		// Move with restrictions
		Move (direction);
		
	}
	
	// Ship movement
	void Move (Vector2 direction)
	{
		// Get the lower-left world coordinate of the viewport.
		Vector2 min = Camera.main.ViewportToWorldPoint(new Vector2(0, 0));
		
		// Get the upper-right world coordinate of the viewport.
		Vector2 max = Camera.main.ViewportToWorldPoint(new Vector2(1, 1));
		
		// Get the coordinates of the player
		Vector2 pos = transform.position;
		
		// Move by the proper amount
		pos += direction  * spaceship.speed * Time.deltaTime;
		
		// Restrict the player from moving outside of the screen.
		pos.x = Mathf.Clamp (pos.x, min.x, max.x);
		pos.y = Mathf.Clamp (pos.y, min.y, max.y);
		
		// Assign the newly-restricted position value.
		transform.position = pos;
	}
	
	// Called at the moment of impact.
	void OnTriggerEnter2D (Collider2D c)
	{
		// Get the layer name
		string layerName = LayerMask.LayerToName(c.gameObject.layer);
		
		// Delete the bullet if the layer name is “Bullet (Enemy)”
		if( layerName == "Bullet (Enemy)")
		{
			// Delete the bullet
			Destroy(c.gameObject);
		}
		
		// Explode if the layer name is “Bullet (Enemy)” or “Enemy”
		if( layerName == "Bullet (Enemy)" || layerName == "Enemy")
		{
			// Find and acquire the Manager component within the scene, and call the GameOver method.
			FindObjectOfType<Manager>().GameOver();
			
			// Explode
			spaceship.Explosion();
			
			// Delete the player
			Destroy (gameObject);
		}
	}
}