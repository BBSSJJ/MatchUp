import {Card, CardFooter, Image, Button} from "@nextui-org/react";
import styles from "./user-info.module.css"

export default function UserProfile() {
	return (
		<div className={styles.container}>
			<Card
      isFooterBlurred
      radius="lg"
      className="border-none w-64 h-48"
			>
				<Image
					alt="Woman listing to music"
					className="object-cover"
					width={200}
					height={150}
					src="https://nextui-docs-v2.vercel.app/images/hero-card-complete.jpeg"
					
				/>
				<CardFooter className="justify-between before:bg-white/10 border-white/20 border-1 overflow-hidden py-1 absolute before:rounded-xl rounded-large bottom-1 w-[calc(100%_-_8px)] shadow-small ml-1 z-10">
					<p className="text-tiny text-white/80">Available soon.</p>
					<Button className="text-tiny text-white bg-black/20" variant="flat" color="default" radius="lg" size="sm">
						Notify me
					</Button>
				</CardFooter>
			</Card>
			<>
				<p>keyword1</p>
				<p>keyword2</p>
				<p>keyword3</p>
				<p>keyword4</p>
				<p>keyword5</p>
			</>
		</div>
	)
}