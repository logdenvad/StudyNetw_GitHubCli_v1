=====================================================================
FUNCTIONAL REQUIREMENTS
=====================================================================
Screen 1 — Repository List (RepoListFragment):
- EditText for organization name input.
- "Search" Button that triggers the network call for GET /orgs/{org}/repos.
- Centered, indeterminate circular ProgressBar shown only while the request is in flight.
- RecyclerView showing the returned repositories.
  - Each item shows: repository name (TextView) and description (TextView, maxLines="3",
    ellipsize="end").
  - If a repository's description is null/empty, show the literal text "No description message".
- Error / empty handling:
  - If the network call fails (HTTP error, IOException, timeout) OR returns an empty list,
    clear the currently displayed list and show a Toast with the exact text:
    "No repos found for organization <orgName>" where <orgName> is exactly what the user typed.
- Tapping a list item navigates (via Navigation Component, passing the org name and repo name
  as Safe-Args-free Bundle arguments) to Screen 2.

Screen 2 — Repository Details (RepoDetailsFragment):
- Centered, indeterminate circular ProgressBar shown only while the request is in flight.
- Calls GET /repos/{org}/{repo} to fetch full repository details independently of Screen 1's data.
- ScrollView containing TextViews for:
  - Name
  - Full description (no line limit / no truncation)
  - Forks count
  - Watchers (subscribers) count
  - Open issues count
  - Parent repository full name — its row/TextView is only visible when "fork" == true for
    this repository; otherwise its container View is set to GONE.
- Toolbar with an Up (back) navigation button wired through the Navigation Component
  (NavigationUI.setupActionBarWithNavController + fragment shows Up as enabled).
