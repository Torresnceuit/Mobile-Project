using UnityEngine;
using System.Collections;

public class Emitter : MonoBehaviour
{
	// Store the Wave prefabs
	public GameObject[] waves;
	
	// Current Wave
	private int currentWave;
	
	// Manager Component
	private Manager manager;
	
	IEnumerator Start ()
	{
		
		// If there are no waves, end the coroutine right away.
		if (waves.Length == 0) {
			yield break;
		}
		
		// Managerコンポーネントをシーン内から探して取得する
		manager = FindObjectOfType<Manager>();
		
		while (true) {
			
			// Wait while the title screen is showing.
			while(manager.IsPlaying() == false) {
				yield return new WaitForEndOfFrame ();
			}
			
			// Make a Wave
			GameObject g = (GameObject)Instantiate (waves [currentWave], transform.position, Quaternion.identity);
			
			// Make the Wave a child of the Emitter
			g.transform.parent = transform;
			
			// Wait until all the Wave’s child Enemy objects have been deleted.
			while (g.transform.childCount != 0) {
				yield return new WaitForEndOfFrame ();
			}
			
			// Delete the Wave
			Destroy (g);
			
			// If all Waves have been used, set currentWave to 0. (Loop from the beginning)
			if (waves.Length <= ++currentWave) {
				currentWave = 0;
			}
			
		}
	}
}