import UserProfile from "@/app/ui/user/user-info"

export default function UserPage({
  params: { id },
}: {
  params: { id :string };
}) {
	return (
		<div>
			<h3>User Page :{id}</h3>
			<UserProfile />
		</div>
	)
}
   