using UnityEngine;

public class Bullet : MonoBehaviour
{
	// Bullet Movement Speed
	public int speed = 10;
	
	// Time from game object creation, until deletion
	public float lifeTime = 5;
	
	void Start ()
	{
		// Move along the local Y-Axis
		rigidbody2D.velocity = transform.up.normalized * speed;
		
		// After lifeTime seconds, delete the object.
		Destroy (gameObject, lifeTime);
	}
}