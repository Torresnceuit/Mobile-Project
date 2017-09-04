using UnityEngine;
using System.Collections;

public class Enemy : MonoBehaviour
{
	// Spaceship Component
	Spaceship spaceship;
	
	IEnumerator Start ()
	{
		
		// Get the Spaceship component
		spaceship = GetComponent<Spaceship> ();
		
		// Move in the negative direction of the Y-Axis
		Move (transform.up * -1);
		
		// If canShot is false, end the coroutine here.
		if (spaceship.canShot == false) {
			yield break;
		}
		
		while (true) {
			
			// Get the child objects
			for (int i = 0; i < transform.childCount; i++) {
				
				Transform shotPosition = transform.GetChild (i);
				
				// Fire a bullet at shotPosition’s position/rotation.
				spaceship.Shot (shotPosition);
			}
			
			// Wait for shotDelay seconds.
			yield return new WaitForSeconds (spaceship.shotDelay);
		}
	}
	
	// Move the ship
	public void Move (Vector2 direction)
	{
		rigidbody2D.velocity = direction * spaceship.speed;
	}
	
	void OnTriggerEnter2D (Collider2D c)
	{
		// Get the Layer name
		string layerName = LayerMask.LayerToName (c.gameObject.layer);
		
		// Return immediately if the layer is not “Bullet (Player)”
		if (layerName != "Bullet (Player)") return;
		
		// Delete the bullet
		Destroy(c.gameObject);
		
		// Explode
		spaceship.Explosion ();
		
		// Delete the enemy
		Destroy (gameObject);
	}
}