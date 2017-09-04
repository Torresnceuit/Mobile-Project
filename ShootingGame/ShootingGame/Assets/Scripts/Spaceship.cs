using UnityEngine;

[RequireComponent(typeof(Rigidbody2D))]
public abstract class Spaceship : MonoBehaviour
{
	// Movement speed
	public float speed;
	
	// Interval at which to fire bullets
	public float shotDelay;
	
	// Bullet prefab
	public GameObject bullet;
	
	// Whether to shoot bullets
	public bool canShot;
	
	// Explosion prefab
	public GameObject explosion;
	
	// Make an explosion
	public void Explosion ()
	{
		Instantiate (explosion, transform.position, transform.rotation);
	}
	
	// Make a bullet
	public void Shot (Transform origin)
	{
		Instantiate (bullet, origin.position, origin.rotation);
	}
	
	protected abstract void Move (Vector2 direction);
}