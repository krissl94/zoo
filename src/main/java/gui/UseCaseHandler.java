package gui;

/**
 * The main application should implement this interface which suplies the
 * handleUseCase method which is invoked by the class UseCaseSet, whenever a
 * usecase Button is clicked.
 */
public interface UseCaseHandler {

	/**
	 * Called with the name of the selected usecase.
	 * 
	 * @param useCaseName
	 *            The name of this usecase.
	 */
	void handleUseCase(String useCaseName);

}
